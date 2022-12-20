export default function ExtractDataSet(data) {
    return data.map(x => {
        return  [(x.time * 1000), x.val.toFixed(1)]
    });
}